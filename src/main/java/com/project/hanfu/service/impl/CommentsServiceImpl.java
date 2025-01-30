package com.project.hanfu.service.impl;

import com.project.hanfu.exception.CustomException;
import com.project.hanfu.mapper.CommentsMapper;
import com.project.hanfu.mapper.UserMapper;
import com.project.hanfu.model.Comments;
import com.project.hanfu.model.User;
import com.project.hanfu.model.dto.InsertCommentsDTO;
import com.project.hanfu.model.dto.QueryCommentsDTO;
import com.project.hanfu.model.dto.UpdateCommentsDTO;
import com.project.hanfu.model.vo.CommentsInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.result.ResultUtil;
import com.project.hanfu.service.CommentsService;
import com.project.hanfu.util.CollectionUtils;
import com.project.hanfu.util.SnowFlake;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private SnowFlake snowFlake;

    @Override
    @Transactional
    public ResultData<CommentsInfoVO> insertComments(InsertCommentsDTO insertCommentsDTO) {
        //获取用户账号
        String account = insertCommentsDTO.getAccount();

        ///获取留言内容
        String comments = insertCommentsDTO.getComments();

        //根据用户id查询用户信息
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("account",account);
        List<User> users = userMapper.selectByExample(userExample);
        User user = users.get(0);
        Long uid = user.getUid();
        String userName = user.getName();

//      数据库查重
        Example commentsExample = new Example(Comments.class);
        commentsExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("comments",comments);
        List<Comments> commentsList = commentsMapper.selectByExample(commentsExample);

        //若存在
        if(CollectionUtils.isNotEmpty(commentsList)){
            throw new CustomException("留言已存在");
        }

        //若不存在 插入数据
        Comments insertComments = new Comments();
        insertComments.setCmid(snowFlake.nextId());
        insertComments.setUid(uid);
        insertComments.setComments(comments);
        commentsMapper.insertSelective(insertComments);

        //返回VO
        CommentsInfoVO commentsInfoVO = new CommentsInfoVO();
        BeanUtils.copyProperties(insertComments,commentsInfoVO);
        commentsInfoVO.setUserName(userName);

        return ResultUtil.getResultData(commentsInfoVO);
    }

    @Override
    public ResultQuery<CommentsInfoVO> selectComments(QueryCommentsDTO queryCommentsDTO) {
        //获取模糊查询条件
        String searchKey = queryCommentsDTO.getSearchKey();

        Example commentsExample = new Example(Comments.class);
        commentsExample.createCriteria().andEqualTo("isdelete",0)
                .andLike("comments","%"+searchKey+"%");
        List<Comments> commentsList = commentsMapper.selectByExample(commentsExample);
        
        //若为空
        if(CollectionUtils.isEmpty(commentsList)){
            return ResultUtil.getResultQuery(new ArrayList<>(),0);
        }
        
        //查询用户信息
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("isdelete",0);
        List<User> userList = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getUid, Function.identity()));

        //若不为空
        List<CommentsInfoVO> commentsInfoVOList = new ArrayList<>();
        for (Comments comments : commentsList) {
            CommentsInfoVO commentsInfoVO = new CommentsInfoVO();
            BeanUtils.copyProperties(comments,commentsInfoVO);
            commentsInfoVO.setUserName(userMap.get(comments.getUid()).getName());
            commentsInfoVO.setCreateTime(comments.getCreateTime());
            commentsInfoVOList.add(commentsInfoVO);
        }

        return ResultUtil.getResultQuery(commentsInfoVOList,commentsList.size());
    }

    @Override
    public ResultData<CommentsInfoVO> replyComments(UpdateCommentsDTO updateCommentsDTO) {
        //获取账号信息
        String account = updateCommentsDTO.getAccount();
        //获取留言id
        Long cmid = updateCommentsDTO.getCmid();

        Example commentsExample = new Example(Comments.class);
        commentsExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("cmid",cmid);
        List<Comments> commentsList = commentsMapper.selectByExample(commentsExample);
        Comments comments = commentsList.get(0);
        Long uid = comments.getUid();

        //查询用户信息
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("isdelete",0);
        List<User> userList = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getUid, Function.identity()));


        //插入数据
        Comments updateComments = new Comments();
        updateComments.setUid(uid);
        updateComments.setCmid(cmid);
        updateComments.setIsreply(1);
        updateComments.setReplyComments(updateCommentsDTO.getReplyComments());
        BeanUtils.copyProperties(updateCommentsDTO,updateComments);
        commentsMapper.updateByExampleSelective(updateComments, commentsExample);

        //返回VO
        CommentsInfoVO commentsInfoVO = new CommentsInfoVO();
        BeanUtils.copyProperties(updateComments,commentsInfoVO);
        commentsInfoVO.setUserName(userMap.get(uid).getName());
        commentsInfoVO.setUpdateTime(updateComments.getUpdateTime());
        commentsInfoVO.setIsreply(updateComments.getIsreply());

        return ResultUtil.getResultData(commentsInfoVO);


    }
}

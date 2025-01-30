package com.project.hanfu.controller;

import com.project.hanfu.model.dto.InsertCommentsDTO;
import com.project.hanfu.model.dto.QueryCommentsDTO;
import com.project.hanfu.model.dto.UpdateCommentsDTO;
import com.project.hanfu.model.vo.CommentsInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    /**
     * 添加留言信息
     * @param insertCommentsDTO
     * @return
     */
    @RequestMapping("/insertComments")
    ResultData<CommentsInfoVO> insertComments(@RequestBody InsertCommentsDTO insertCommentsDTO){
        return commentsService.insertComments(insertCommentsDTO);
    }

    /**
     * 查询留言信息
     * @param queryCommentsDTO
     * @return
     */
    @RequestMapping("/selectComments")
    ResultQuery<CommentsInfoVO> selectComments(@RequestBody QueryCommentsDTO queryCommentsDTO){
        return commentsService.selectComments(queryCommentsDTO);
    }

    /**
     * 管理员回复留言模块
     * @param updateCommentsDTO
     * @return
     */
    @RequestMapping("/replyComments")
    ResultData<CommentsInfoVO> replyComments(@RequestBody UpdateCommentsDTO updateCommentsDTO){
        return commentsService.replyComments(updateCommentsDTO);
    }
}

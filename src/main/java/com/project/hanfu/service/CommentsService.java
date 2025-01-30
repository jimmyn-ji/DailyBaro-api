package com.project.hanfu.service;

import com.project.hanfu.model.dto.InsertCommentsDTO;
import com.project.hanfu.model.dto.QueryCommentsDTO;
import com.project.hanfu.model.dto.UpdateCommentsDTO;
import com.project.hanfu.model.vo.CommentsInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import org.springframework.web.bind.annotation.RequestBody;

public interface CommentsService {

    /**
     * 添加留言信息
     * @param insertCommentsDTO
     * @return
     */
    ResultData<CommentsInfoVO> insertComments(InsertCommentsDTO insertCommentsDTO);

    /**
     * 查询留言信息
     * @param queryCommentsDTO
     * @return
     */
    ResultQuery<CommentsInfoVO> selectComments(QueryCommentsDTO queryCommentsDTO);

    /**
     * 回复留言信息
     * @param updateCommentsDTO
     * @return
     */
    ResultData<CommentsInfoVO> replyComments(UpdateCommentsDTO updateCommentsDTO);
}

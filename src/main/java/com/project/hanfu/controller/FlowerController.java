package com.project.hanfu.controller;

import com.project.hanfu.config.Constant;
import com.project.hanfu.config.HttpMsg;
import com.project.hanfu.mapper.FlowersDao;
import com.project.hanfu.model.Flower;
import com.project.hanfu.menu.StatusCode;
import com.project.hanfu.model.dto.HanfuQueryDTO;
import com.project.hanfu.model.vo.HanfuInfoVO;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.service.FlowersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 鲜花 控制层
 *
 * @author: ShanZhu
 * @date: 2024-01-24
 */
@RestController
@RequestMapping("flower")
public class FlowerController {

    @Value("${uploadPath}")
    private String uploadPath;

    @Resource
    private FlowersService flowerService;
    @Resource
    private FlowersDao flowersDao;


    /**
     * 分页查询汉服信息
     * @param page
     * @param searchKey
     * @param searchType
     * @return
     */
    @RequestMapping("/find")
    ResultQuery<HanfuInfoVO> find(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey, @RequestParam("searchType") String searchType) {
        //从 URL 查询字符串中接收数据并转化为 JSON
        HanfuQueryDTO hanfuQueryDTO = new HanfuQueryDTO();
        hanfuQueryDTO.setPage(page);
        hanfuQueryDTO.setSearchKey(searchKey);
        hanfuQueryDTO.setSearchType(searchType);
        return flowerService.find(hanfuQueryDTO);
    }

    /**
     * 管理员查询所有
     *
     * @param page      分页页数
     * @param searchKey 查询条件
     * @return 结果
     */
    @RequestMapping("/findAll")
    ResultBase findAll(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey) {
        ResultBase resultBase = new ResultBase();
        List<Flower> flowers = flowerService.findAll(searchKey);

        if (flowers == null) {
            return resultBase.setCode(StatusCode.SUCCESS);
        }

        return getResponse(flowers, page, Constant.PAGE_SIZE, resultBase);
    }

    /**
     * 创建鲜花商品
     *
     * @param flower 商品信息
     * @return 结果
     */
    @RequestMapping("/create")
    ResultBase create(@RequestBody Flower flower) {
        ResultBase resultBase = new ResultBase();

        int ans = flowerService.add(flower);
        if (ans == 1) {
            return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.ADD_FLOWER_OK);
        }

        return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.ADD_FLOWER_FAILED);
    }

    /**
     * 更新鲜花商品
     *
     * @param flower 商品信息
     * @return 结果
     */
    @RequestMapping("/update")
    ResultBase update(@RequestBody Flower flower) {
        ResultBase resultBase = new ResultBase();

        int ans = flowerService.update(flower);
        if (ans >= 0) {
            return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.UPDATE_FLOWER_OK);
        }

        return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.UPDATE_FLOWER_FAILED);
    }

    /**
     * 改版商品状态
     *
     * @param flower 商品信息
     * @return 结果
     */
    @RequestMapping("/changeState")
    ResultBase changeState(@RequestBody Flower flower) {
        ResultBase resultBase = new ResultBase();

        flowersDao.changeState(flower);
        String msg = flower.getState() == 1 ? HttpMsg.GOODS_UP_OK : HttpMsg.GOODS_DOWN_OK;

        return resultBase.setCode(StatusCode.SUCCESS).setMessage(msg);
    }

    /**
     * 上传图片
     *
     * @param file 文件信息
     * @return 结果
     */
    @RequestMapping("/updateImg")
    ResultBase updateImg(@RequestParam("file") MultipartFile file) {
        ResultBase resultBase = new ResultBase();

        // 只接收 jpg/png 图片
        String filename = file.getOriginalFilename();
        if (!filename.endsWith(".jpg") && !filename.endsWith(".png")) {
            return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.ERROR_FILE_TYPE);
        }

        String img_guid = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";

        try {
            savePic(file.getInputStream(), img_guid);
            return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.UPDATE_PIC_OK).setData(img_guid);
        } catch (IOException e) {
            return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.UPDATE_PIC_FAILED);
        }

    }

    /**
     * 更新图片guid
     *
     * @param guid guid
     * @param id   id
     * @return 结果
     */
    @PutMapping("/updateImgGuid")
    ResultBase updateImgGuid(@RequestParam("guid") String guid, @RequestParam("id") int id) {
        ResultBase resultBase = new ResultBase();

        int ans = flowerService.updateImg(guid, id);
        if (ans == 1) {
            return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.UPDATE_PIC_OK);
        }

        return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.UPDATE_PIC_FAILED);
    }

    /**
     * 删除图片
     *
     * @param id 图片id
     * @return 结果
     */
    @DeleteMapping("/delete")
    ResultBase delete(@RequestParam("id") int id) {
        ResultBase resultBase = new ResultBase();

        int ans = flowerService.delete(id);
        if (ans == 1) {
            return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.DELETE_FLOWER_OK);
        }

        return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.DELETE_FLOWER_FAILED);
    }

    private void savePic(InputStream inputStream, String fileName) {
        OutputStream os = null;
        try {
            String path = uploadPath;
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            os = new FileOutputStream(new File(path + fileName));
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ResultBase getResponse(List<Flower> flowers, int page, int pageSize, ResultBase resultBase) {
        Map<String, Object> map = new HashMap<>();
        List<Flower> items = flowers.size() >= page * pageSize ?
                flowers.subList((page - 1) * pageSize, page * pageSize)
                : flowers.subList((page - 1) * pageSize, flowers.size());
        int len = flowers.size() % pageSize == 0 ? flowers.size() / pageSize
                : (flowers.size() / pageSize + 1);
        for (Flower item : items) {
            if (item.getImg_guid() == null) {
                item.setImg_guid(Constant.DEFAULT_IMG);
            }
            item.setImg_guid(Constant.IMG_USE_PATH + item.getImg_guid());
        }
        map.put("items", items);
        map.put("len", len);
        return resultBase.setCode(StatusCode.SUCCESS).setData(map);
    }

}


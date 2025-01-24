package com.project.hanfu.controller;

import com.project.hanfu.config.Constant;
import com.project.hanfu.config.HttpMsg;
import com.project.hanfu.menu.StatusCode;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.model.Species;
import com.project.hanfu.service.SpeciesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 鲜花种类 控制层
 *
 * @author: ShanZhu
 * @date: 2024-01-24
 */
@RestController
@RequestMapping("species")
public class FlowerTypeController {

    @Resource
    private SpeciesService speciesService;

    @RequestMapping("/findAll")
    ResultBase findAll() {
        ResultBase resultBase = new ResultBase();
        List<Species> all = speciesService.findAll();
        if (all.size()<=0){
            return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.NO_TYPE_NOW);
        }
        return resultBase.setCode(StatusCode.SUCCESS).setData(all);
    }

    @RequestMapping("/find")
    ResultBase find(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey) {
        ResultBase resultBase = new ResultBase();
        Map<String, Object> map = new HashMap<>();
        List<Species> list = speciesService.find(searchKey);
        if (list == null) {
            return resultBase.setCode(StatusCode.SUCCESS);
        }
        List<Species> items = list.size() >= page * Constant.PAGE_SIZE ?
                list.subList((page - 1) * Constant.PAGE_SIZE, page * Constant.PAGE_SIZE)
                : list.subList((page - 1) * Constant.PAGE_SIZE, list.size());
        int len = list.size() % Constant.PAGE_SIZE == 0 ? list.size() / Constant.PAGE_SIZE
                : (list.size() / Constant.PAGE_SIZE + 1);
        map.put("items", items);
        map.put("len", len);
        return resultBase.setCode(StatusCode.SUCCESS).setData(map);
    }

    @RequestMapping("/create")
    ResultBase create(@RequestBody Species species) {
        ResultBase resultBase = new ResultBase();
        try {
            speciesService.add(species);
            return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.ADD_TYPE_OK);
        } catch (Exception e) {
            return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.ADD_TYPE_FAILED);
        }
    }

    @RequestMapping("/update")
    ResultBase update(@RequestBody Species species) {
        ResultBase resultBase = new ResultBase();
        try {
            speciesService.update(species);
            return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.UPDATE_TYPE_OK);
        } catch (Exception e) {
            return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.UPDATE_TYPE_FAILED);
        }
    }

    @DeleteMapping("/delete")
    ResultBase delete(@RequestParam("id") int id) {
        ResultBase resultBase = new ResultBase();
        try {
            speciesService.delete(id);
            return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.DELETE_TYPE_OK);
        } catch (Exception e) {
            return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.DELETE_TYPE_FAILED);
        }
    }

}


package com.atguigu.controller;

import base.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseImage;
import com.atguigu.service.HouseImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import result.Result;
import util.FileUtil;
import util.QiniuUtils;

import java.io.IOException;

@Controller
@RequestMapping("/houseImage")
public class HouseImageController extends BaseController {
    @Reference
    private HouseImageService houseImageService;

    private static final String UPLOAD_SHOW = "house/upload";
    private static final String SHOW_ACTION = "redirect:/house";

    /**
     * 进入上传页面
     */
    @RequestMapping("uploadShow/{houseId}/{type}")
    public String toUploadShow(@PathVariable Long houseId, @PathVariable Integer type, Model model) {
        model.addAttribute("houseId", houseId);
        model.addAttribute("type", type);
        return UPLOAD_SHOW;
    }

    @RequestMapping("upload/{houseId}/{type}")
    @ResponseBody
    public Result upload(@PathVariable Long houseId,
                         @PathVariable Integer type,
                         @RequestParam("file") MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {

            String originalFilename = file.getOriginalFilename();
            String uuidName = FileUtil.getUUIDName(originalFilename);

            QiniuUtils.upload2Qiniu(file.getBytes(),uuidName);

            String url = QiniuUtils.getUrl(uuidName);
            HouseImage houseImage = new HouseImage();
            houseImage.setImageName(uuidName);
            houseImage.setImageUrl(url);
            houseImage.setHouseId(houseId);
            houseImage.setType(type);
            houseImageService.insert(houseImage);
        }
        return Result.ok();
    }

//    '/houseImage/delete/[[${house.id}]]/'+id
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId, @PathVariable Long id)
    {
        HouseImage houseImage = houseImageService.getById(id);

        houseImageService.delete(id);

        QiniuUtils.deleteFileFromQiniu(houseImage.getImageName());

        return SHOW_ACTION+"/"+houseId;
    }
}


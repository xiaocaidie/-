package li.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import li.common.R;
import li.dto.SetmealDto;
import li.entity.Category;
import li.entity.Setmeal;
import li.entity.SetmealDish;
import li.service.CategoryService;
import li.service.SetmealDishService;
import li.service.SetmealSerive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealSerive setmealSerive;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealSerive.saveWithDish(setmealDto);
        return R.success("新增套餐成功");

    }

    @GetMapping("/page")
    public R<Page>page(int page,int pageSize,String name){
        //分页构造器对象
        Page<Setmeal>pageInfo=new Page<>(page,pageSize);
        Page<SetmealDto>dtoPage=new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealSerive.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto>list=records.stream().map((item)->{
            SetmealDto setmealDto=new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item,setmealDto);

            Long categoryId=item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if(category!=null){
                String categoryName=category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }


    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String>delete(@RequestParam List<Long>ids){
        log.info("ids:{}",ids);
        setmealSerive.removeWithDish(ids);
        return R.success("删除成功");
    }
}

package li.controller;

import ch.qos.logback.classic.spi.EventArgUtil;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGASTVisitor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import li.common.R;
import li.dto.DishDto;
import li.entity.Category;
import li.entity.Dish;
import li.service.CategoryService;
import li.service.DishFlavorservice;
import li.service.DishSerive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishSerive dishSerive;

    @Autowired
    private DishFlavorservice dishFlavorservice;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishSerive.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /**
     *菜品信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page>page(int page,int pageSize,String name){

        //构造分页构造器
        Page<Dish>pageInfo=new Page<>(page,pageSize);
        Page<DishDto>dishDtoPage=new Page<>();
        //条件构造器
        LambdaQueryWrapper<Dish>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Dish::getName,name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishSerive.page(pageInfo,queryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        List<Dish>records=pageInfo.getRecords();

        List<DishDto>list=records.stream().map((item)->{
            DishDto dishDto=new DishDto();
            BeanUtils.copyProperties(item,dishDto);

            Long categoryId=item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if(category!=null)
            {
                String categoryName=category.getName();
                dishDto.setCategoryName(categoryName);
            }


            return dishDto;

        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return  R.success(dishDtoPage);
    }

    /**
     *
     * 根据id查询菜品信息和对相应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto>get(@PathVariable Long id){
        DishDto byIdWithFlavor = dishSerive.getByIdWithFlavor(id);

        return R.success(byIdWithFlavor);
    }
    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishSerive.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }


    /**
     *
     * 根据条件查询对应的菜品信息
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<Dish>>List(Dish dish){

        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());

        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish>dishList=dishSerive.list(queryWrapper);
        return R.success(dishList);
    }

}

package li.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import li.dto.DishDto;
import li.entity.Dish;
import li.entity.DishFlavor;
import li.mapper.DishMapper;
import li.service.DishFlavorservice;
import li.service.DishSerive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishSeriveImpl extends ServiceImpl<DishMapper, Dish> implements DishSerive {
    /**
     *
     * 新增菜品，同时保存对应的口味数据
     * @param dishDto
     */
    @Autowired
    private DishFlavorservice dishFlavorservice;
    @Transactional
    public void saveWithFlavor(DishDto dishDto){
        //保存菜品的基本信息到dish
        this.save(dishDto);
        Long dishId = dishDto.getId();//菜品Id
        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors= flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味的基本信息到dish_flavor
       dishFlavorservice.saveBatch(flavors);



    }

    /**
     *
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = this.getById(id);
        DishDto dishDto=new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorservice.list(queryWrapper);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表基本信息
        this.updateById(dishDto);
        //清理当前菜品对应口味数据--dish_flavor表的delect操作
        LambdaQueryWrapper<DishFlavor>queryWrappe=new LambdaQueryWrapper<>();
        queryWrappe.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorservice.remove(queryWrappe);
        //添加当前提交过来的口味数据---dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors= flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorservice.saveBatch(flavors);


    }
}

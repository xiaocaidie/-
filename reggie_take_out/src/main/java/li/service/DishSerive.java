package li.service;

import com.baomidou.mybatisplus.extension.service.IService;
import li.dto.DishDto;
import li.entity.Dish;
import li.entity.Setmeal;


public interface DishSerive extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表
    public void saveWithFlavor(DishDto dishDto);
    //根据id查询菜品信息和口味信息
    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}

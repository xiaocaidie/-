package li.service;

import com.baomidou.mybatisplus.extension.service.IService;
import li.dto.SetmealDto;
import li.entity.Dish;
import li.entity.Setmeal;

import java.util.List;

public interface SetmealSerive extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    public void removeWithDish(List<Long>ids);
}

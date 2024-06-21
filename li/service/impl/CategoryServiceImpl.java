package li.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import li.common.CustomException;
import li.entity.Category;
import li.entity.Dish;
import li.entity.Setmeal;
import li.mapper.CategoryMapper;
import li.service.CategoryService;
import li.service.DishSerive;
import li.service.SetmealSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishSerive dishSerive;

    @Autowired
    private SetmealSerive setmealSerive;
    /**
     * 根据id删除分类，删除前需要进行判断
     * @param ids
     */
    @Override
    public void remove(Long ids) {
        LambdaQueryWrapper<Dish>dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, ids);
        //查询当前分类是否关联了菜品，如果已经关联，抛出业务异常
        int count1 = dishSerive.count(dishLambdaQueryWrapper);
        if (count1 > 0) {
            //已经关联菜品，抛出异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        //查询当前分类是否关联了套餐，如果已关联，抛出业务异常
        LambdaQueryWrapper<Setmeal>setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, ids);
        int count2 = setmealSerive.count(setmealLambdaQueryWrapper);
        if (count2 > 0) {
            //已经关联套餐，异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        //正常删除
        super.removeById(ids);

    }
}

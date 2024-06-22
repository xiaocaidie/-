package li.controller;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import li.common.R;
import li.entity.Category;
import li.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * \新增分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String>save(@RequestBody Category category) {
        log.info("category: {}", category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 分页
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")

    public R<Page>page(int page,int pageSize){
        Page<Category>pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id删除分类
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("删除分类，id: {}", ids);
        //categoryService.removeById(ids);
        categoryService.remove(ids);
        return R.success("分类信息删除成分功");
    }

    /**
     * 根据id修改分类信息
     */
    @PutMapping
    public R<String>update(@RequestBody Category category){
        log.info("修改分类信息: {}", category);
        categoryService.updateById(category);

        return R.success("修改分类信息成功");
    }

    @GetMapping("/list")
    public R<List<Category>>list(Category category){
        LambdaQueryWrapper<Category>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category>categoryList=categoryService.list(queryWrapper);
        return R.success(categoryList);
    }
}

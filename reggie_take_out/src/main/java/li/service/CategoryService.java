package li.service;

import com.baomidou.mybatisplus.extension.service.IService;
import li.entity.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long  ids);
}

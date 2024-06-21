package li.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import li.entity.DishFlavor;
import li.mapper.DishFlavorMapper;
import li.service.DishFlavorservice;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>implements DishFlavorservice {
}

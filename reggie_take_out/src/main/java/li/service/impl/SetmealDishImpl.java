package li.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import li.entity.SetmealDish;
import li.mapper.SetmealDishMapper;
import li.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealDishImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}

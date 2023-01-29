package com.zr.service.car;

import com.zr.vo.car.Work;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkService{
    List<Work> list(Work work);

    Work listDetail(Long id);

    Work listOutDetail(Long id);

    Work listInDetail(Long id);
}

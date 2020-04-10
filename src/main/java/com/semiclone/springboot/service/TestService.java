package com.semiclone.springboot.service;

import com.semiclone.springboot.repository.TestRepository;
import com.semiclone.springboot.repository.entity.Test;
import com.semiclone.springboot.web.dto.TestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestService {

    private final TestRepository testRepository;

    public TestDto findById(Long id){

        Test test = testRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("실패 id=" + id));

        return new TestDto(test);
    }


}

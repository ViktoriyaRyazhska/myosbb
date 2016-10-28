package com.softserve.osbb.service.impl;

import com.softserve.osbb.model.Option;
import com.softserve.osbb.repository.OptionRepository;
import com.softserve.osbb.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Roman on 28.07.2016.
 */
@Service
public class OptionServiceImpl implements OptionService{

    @Autowired
    private OptionRepository optionRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Option addOption(Option option) {
        return optionRepository.saveAndFlush(option);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Option getOption(Integer id) {
        return optionRepository.findOne(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Option> getAllOption() {
        return optionRepository.findAll();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Option updateOption(Option option) {

        if(optionRepository.exists(option.getOptionId())) {
            return optionRepository.save(option);
        } else {
            throw new IllegalArgumentException("Option with id=" + option.getOptionId()
                    + " doesn't exist. First try to add this option.");
        }
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteOption(Integer id) {
        optionRepository.delete(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteOption(Option option) {
        optionRepository.delete(option);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteAllOption() {
        optionRepository.deleteAll();
    }
}

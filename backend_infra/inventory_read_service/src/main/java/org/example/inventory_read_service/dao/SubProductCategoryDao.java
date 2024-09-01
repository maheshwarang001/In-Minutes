package org.example.inventory_read_service.dao;


import org.example.inventory_read_service.entity.inventory.subProductCategory.SubProductCategory;
import org.example.inventory_read_service.repository.SubProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SubProductCategoryDao {


        @Autowired
        private SubProductCategoryRepository subProductCategoryRepository;


        public Optional<SubProductCategory> findBySubProductCategoryId(UUID suProductCategoryId){
            return subProductCategoryRepository.findSubProductCategoryByName(suProductCategoryId);
        }

        public void deleteById(UUID id){
            subProductCategoryRepository.deleteById(id);
        }

        public void save(SubProductCategory subProductCategory){
            subProductCategoryRepository.save(subProductCategory);
        }




}

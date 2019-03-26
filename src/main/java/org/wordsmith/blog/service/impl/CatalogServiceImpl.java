package org.wordsmith.blog.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wordsmith.blog.domain.Catalog;
import org.wordsmith.blog.domain.User;
import org.wordsmith.blog.repository.CatalogRepository;
import org.wordsmith.blog.service.CatalogService;


/**
 * Catalog 服务接口实现.
 * @author Wordsmith
 */
@Service
public class CatalogServiceImpl implements CatalogService {
	
	@Autowired
	private CatalogRepository catalogRepository;
	
	@Override
	public Catalog saveCatalog(Catalog catalog) {
		 // 判断重复
        List<Catalog> list = catalogRepository.findByUserAndName(catalog.getUser(),
        		catalog.getName());
        if(list !=null && list.size() > 0) {
            throw new IllegalArgumentException("该分类已经存在了");
        }
        return catalogRepository.save(catalog);
	}
	
	@Override
	public void removeCatalog(Long id) {
		catalogRepository.deleteById(id);
	}

	@Override
	public Optional<Catalog> getCatalogById(Long id) {
		return catalogRepository.findById(id);
	} 

	@Override
	public List<Catalog> listCatalogs(User user) {
		return catalogRepository.findByUser(user);
	}

}

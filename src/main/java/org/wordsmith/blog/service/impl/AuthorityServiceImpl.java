package org.wordsmith.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wordsmith.blog.service.AuthorityService;

import org.wordsmith.blog.domain.Authority;
import org.wordsmith.blog.repository.AuthorityRepository;

/**
 * Authority 服务.
 * @author Wordsmith
 *
 */
@Service
public class AuthorityServiceImpl  implements AuthorityService {
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Override
	public Authority getAuthorityById(Long id) {
		return authorityRepository.findById(id).get();
	}

}

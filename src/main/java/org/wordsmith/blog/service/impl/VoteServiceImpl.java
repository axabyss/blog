package org.wordsmith.blog.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wordsmith.blog.domain.Blog;
import org.wordsmith.blog.domain.Vote;
import org.wordsmith.blog.repository.VoteRepository;
import org.wordsmith.blog.service.VoteService;


/**
 * Vote 服务.
 * @author Wordsmith
 */
@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	private VoteRepository voteRepository;
	
	@Override
	@Transactional
	public void removeVote(Long id) {
		voteRepository.deleteById(id);
	}
	@Override
	public Vote getVoteById(Long id) {
		return voteRepository.findById(id).get();
	}

}

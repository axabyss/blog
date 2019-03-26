package org.wordsmith.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.wordsmith.blog.domain.Vote;

/**
 * Vote资源库
 * @author Wordsmith
 *
 */
public interface VoteRepository extends JpaRepository<Vote, Long>{
	
}

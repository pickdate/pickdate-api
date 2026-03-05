package com.pickdate.poll.application;

import com.pickdate.bootstrap.domain.Identifier;
import com.pickdate.poll.domain.Vote;
import com.pickdate.poll.domain.VoteData;
import com.pickdate.poll.domain.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
@CacheConfig(cacheNames = "votes")
class VoteService implements VoteUseCase {

    private final VoteRepository repository;

    @Override
    @CacheEvict(key = "#command.pollId().value()")
    public void castVote(CastVoteCommand command) {
        var vote = new Vote()
                .with(new Vote.VoteId(command.participantId(), command.OptionId()))
                .with(command.pollId())
                .with(command.vote());
        repository.save(vote);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(key = "#pollId.value()")
    public List<VoteData> getVotesBy(Identifier pollId) {
        return repository.findByPollId(pollId).stream()
                .map(Vote::toData)
                .toList();
    }
}

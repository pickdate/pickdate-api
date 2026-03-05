package com.pickdate.poll.application;

import com.pickdate.bootstrap.domain.Identifier;
import com.pickdate.bootstrap.domain.Property;
import com.pickdate.bootstrap.exception.NotFoundException;
import com.pickdate.poll.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
@CacheConfig(cacheNames = "polls")
public class PollService implements PollUseCase {

    private final PollRepository repository;

    @Override
    @CacheEvict(allEntries = true)
    public PollData createPoll(Title title, Description description) {
        var poll = Poll.from(title, description);
        var saved = repository.save(poll);
        return PollMapper.toPollData(saved);
    }

    @Override
    @CacheEvict(key = "#pollId.value()")
    public void deletePoll(Identifier pollId) {
        repository.deleteById(pollId);
    }

    @Override
    @CacheEvict(key = "#pollId.value()")
    public PollData registerParticipant(Identifier pollId, Participant participant) {
        var poll = findPoll(pollId);
        poll.addParticipant(participant);
        repository.save(poll);
        return PollMapper.toPollData(poll);
    }

    @Override
    @CacheEvict(key = "#pollId.value()")
    public PollData addOption(Identifier pollId, TimeRange timeRange, boolean wholeDay) {
        var poll = findPoll(pollId);
        poll.addOption(timeRange, wholeDay);
        repository.save(poll);
        return PollMapper.toPollData(poll);
    }

    @Override
    @Cacheable(key = "#id.value()")
    @Transactional(readOnly = true)
    public PollData getPoll(Identifier id) {
        var poll = findPoll(id);
        return PollMapper.toPollData(poll);
    }

    @Override
    @CacheEvict(key = "#pollId.value()")
    public PollData addLocation(Identifier pollId, LocationData location) {
        var poll = findPoll(pollId);
        var geoLocation = GeoLocation.of(location);
        poll.addLocation(geoLocation);
        repository.save(poll);
        return PollMapper.toPollData(poll);
    }

    private Poll findPoll(Identifier id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(new Property<>("id", id), "Poll not found"));
    }
}

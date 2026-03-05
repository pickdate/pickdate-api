package com.pickdate.poll.infrastructure;

import com.pickdate.bootstrap.domain.Identifier;
import com.pickdate.poll.application.PollUseCase;
import com.pickdate.poll.domain.PollData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/api/v1/polls")
@RequiredArgsConstructor
@Tag(name = "Polls", description = "Manage polls and options")
@SecurityRequirement(name = "basicAuth")
class PollApi {

    private final PollUseCase pollUseCase;

    @GetMapping("/{pollId}")
    @Operation(summary = "Get poll by id", description = "Returns poll details for given identifier")
    ResponseEntity<PollResponse> getPoll(@PathVariable String pollId) {
        var data = pollUseCase.getPoll(Identifier.of(pollId));
        var response = PollResponse.from(data);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create new poll", description = "Creates a new poll with provided title and description")
    ResponseEntity<PollData> createPoll(@Valid @RequestBody CreatePollRequest req) {
        var data = pollUseCase.createPoll(req.getTitle(), req.getDescription());
        return ResponseEntity.status(CREATED).body(data);
    }

    @DeleteMapping("/{pollId}")
    @Operation(summary = "Delete poll", description = "Deletes poll by given identifier")
    ResponseEntity<?> deletePoll(@PathVariable String pollId) {
        pollUseCase.deletePoll(Identifier.of(pollId));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{pollId}/options")
    @ResponseStatus(CREATED)
    @Operation(summary = "Add option to poll", description = "Adds an option (time range or whole-day) to an existing poll")
    ResponseEntity<PollData> addOption(
            @PathVariable String pollId,
            @Valid @RequestBody CreateOptionRequest request
    ) {
        var data = pollUseCase.addOption(Identifier.of(pollId), request.getRange(), request.isWholeDay());
        return ResponseEntity.status(CREATED).body(data);
    }

    @PostMapping("/{pollId}/participants")
    @Operation(summary = "Register participant", description = "Registers a participant in the poll with optional email")
    ResponseEntity<PollData> registerParticipant(
            @PathVariable String pollId,
            @RequestBody RegisterParticipantRequest request
    ) {
        var data = pollUseCase.registerParticipant(Identifier.of(pollId), request.toParticipant());
        return ResponseEntity.status(CREATED).body(data);
    }

    @PostMapping("/{pollId}/location")
    @Operation(summary = "Add location to poll", description = "Adds a location to an existing poll")
    ResponseEntity<PollData> addLocation(
            @PathVariable String pollId,
            @RequestBody CreateLocationRequest request
    ) {
        var data = pollUseCase.addLocation(Identifier.of(pollId), request.toLocationData());
        return ResponseEntity.status(CREATED).body(data);
    }
}

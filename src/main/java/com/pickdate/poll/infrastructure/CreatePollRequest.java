package com.pickdate.poll.infrastructure;

import com.pickdate.poll.domain.Description;
import com.pickdate.poll.domain.Title;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
class CreatePollRequest {

    // TODO: add validation messages
    @Size.List({
            @Size(min = 3, message = "{validation.name.size.too_short}"),
            @Size(max = 255, message = "{validation.name.size.too_long}")
    })
    @NotBlank
    private String title;

    private String description;

    Title getTitle() {
        return Title.of(title);
    }

    Description getDescription() {
        return Description.ofNullable(description);
    }
}

package com.mock.yatra.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class GPTResponse {
	private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Choice {
        private GPTMessage message;

        public GPTMessage getMessage() {
            return message;
        }
    }
}

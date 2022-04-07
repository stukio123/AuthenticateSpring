package com.example.userservice.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessages {
    private String code;
    private String messages;
    private String data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseMessages that = (ResponseMessages) o;
        return Objects.equals(code, that.code) && Objects.equals(messages, that.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, messages, data);
    }

    @Override
    public String toString() {
        return "ResponseMessages{" +
                "code='" + code + '\'' +
                ", messages='" + messages + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}

package com.app.lms.modules.profile.dtos;

import com.app.lms.modules.account.dtos.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO<T> {
    private AccountDTO account;
    private T profile;

    public ProfileDTO(T profile, AccountDTO account) {
        this.profile = profile;
        this.account = account;
    }
}

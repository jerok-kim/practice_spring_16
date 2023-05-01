package kim.jerok.practice_spring_16.model;

import kim.jerok.practice_spring_16.model.board.Board;
import kim.jerok.practice_spring_16.model.user.User;

public class MyDummyEntity {

    protected User newUser(String username) {
        return User.builder()
                .username(username)
                .password("1234")
                .email(username + "@gmail.com")
                .build();
    }

    protected Board newBoard(String title) {
        return Board.builder()
                .title(title)
                .content(title)
                .author("jerok")
                .build();
    }

}

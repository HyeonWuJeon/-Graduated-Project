package com.example.demo.APITest;

import com.example.demo.config.ProfileController;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileControllerUnitTest {

    @Test
    public void real_profile_search() {
        //given
        String expectedProfile = "real";

        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.proflie();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void real_profile_x () {
        //given
        String expectedProfile = "oauth";
        MockEnvironment env = new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.proflie();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    @DisplayName("Default 조회")
    public void active_profile(){
        //given
        String expectedProfile ="default";
        MockEnvironment env = new MockEnvironment();
        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.proflie();

        //then
        assertEquals(profile, expectedProfile);

    }
}

package br.com.jofofe.campanha.utils;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class Util {

    private Util() {
    }

    public static final ResultMatcher statusMatcher = MockMvcResultMatchers.status().is(new StatusMatcher());

    private static class StatusMatcher extends BaseMatcher<Integer> {
        @Override
        public boolean matches(Object item) {
            if (item instanceof Number) {
                int status = ((Number) item).intValue();
                System.err.println("Status: " + status);
            }
            return true;
        }
        @Override
        public void describeTo(Description description) {
            description.appendText("Status Matcher");
        }
    }

}

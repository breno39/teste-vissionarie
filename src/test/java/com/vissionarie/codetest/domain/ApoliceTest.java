package com.vissionarie.codetest.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vissionarie.codetest.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApoliceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Apolice.class);
        Apolice apolice1 = new Apolice();
        apolice1.setId("id1");
        Apolice apolice2 = new Apolice();
        apolice2.setId(apolice1.getId());
        assertThat(apolice1).isEqualTo(apolice2);
        apolice2.setId("id2");
        assertThat(apolice1).isNotEqualTo(apolice2);
        apolice1.setId(null);
        assertThat(apolice1).isNotEqualTo(apolice2);
    }
}

package com.vissionarie.codetest.web.rest;

import static com.vissionarie.codetest.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vissionarie.codetest.IntegrationTest;
import com.vissionarie.codetest.domain.Apolice;
import com.vissionarie.codetest.repository.ApoliceRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ApoliceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApoliceResourceIT {

    private static final Long DEFAULT_NUMERO = 1L;
    private static final Long UPDATED_NUMERO = 2L;

    private static final LocalDate DEFAULT_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FIM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PLACA_VEICULO = "AAAAAAAAAA";
    private static final String UPDATED_PLACA_VEICULO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/apolices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ApoliceRepository apoliceRepository;

    @Autowired
    private MockMvc restApoliceMockMvc;

    private Apolice apolice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apolice createEntity() {
        Apolice apolice = new Apolice()
            .numero(DEFAULT_NUMERO)
            .inicio(DEFAULT_INICIO)
            .fim(DEFAULT_FIM)
            .placaVeiculo(DEFAULT_PLACA_VEICULO)
            .valor(DEFAULT_VALOR);
        return apolice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apolice createUpdatedEntity() {
        Apolice apolice = new Apolice()
            .numero(UPDATED_NUMERO)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .placaVeiculo(UPDATED_PLACA_VEICULO)
            .valor(UPDATED_VALOR);
        return apolice;
    }

    @BeforeEach
    public void initTest() {
        apoliceRepository.deleteAll();
        apolice = createEntity();
    }

    @Test
    void createApolice() throws Exception {
        int databaseSizeBeforeCreate = apoliceRepository.findAll().size();
        // Create the Apolice
        restApoliceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apolice)))
            .andExpect(status().isCreated());

        // Validate the Apolice in the database
        List<Apolice> apoliceList = apoliceRepository.findAll();
        assertThat(apoliceList).hasSize(databaseSizeBeforeCreate + 1);
        Apolice testApolice = apoliceList.get(apoliceList.size() - 1);
        assertThat(testApolice.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testApolice.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testApolice.getFim()).isEqualTo(DEFAULT_FIM);
        assertThat(testApolice.getPlacaVeiculo()).isEqualTo(DEFAULT_PLACA_VEICULO);
        assertThat(testApolice.getValor()).isEqualByComparingTo(DEFAULT_VALOR);
    }

    @Test
    void createApoliceWithExistingId() throws Exception {
        // Create the Apolice with an existing ID
        apolice.setId("existing_id");

        int databaseSizeBeforeCreate = apoliceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApoliceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apolice)))
            .andExpect(status().isBadRequest());

        // Validate the Apolice in the database
        List<Apolice> apoliceList = apoliceRepository.findAll();
        assertThat(apoliceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = apoliceRepository.findAll().size();
        // set the field null
        apolice.setInicio(null);

        // Create the Apolice, which fails.

        restApoliceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apolice)))
            .andExpect(status().isBadRequest());

        List<Apolice> apoliceList = apoliceRepository.findAll();
        assertThat(apoliceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkFimIsRequired() throws Exception {
        int databaseSizeBeforeTest = apoliceRepository.findAll().size();
        // set the field null
        apolice.setFim(null);

        // Create the Apolice, which fails.

        restApoliceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apolice)))
            .andExpect(status().isBadRequest());

        List<Apolice> apoliceList = apoliceRepository.findAll();
        assertThat(apoliceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPlacaVeiculoIsRequired() throws Exception {
        int databaseSizeBeforeTest = apoliceRepository.findAll().size();
        // set the field null
        apolice.setPlacaVeiculo(null);

        // Create the Apolice, which fails.

        restApoliceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apolice)))
            .andExpect(status().isBadRequest());

        List<Apolice> apoliceList = apoliceRepository.findAll();
        assertThat(apoliceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllApolices() throws Exception {
        // Initialize the database
        apoliceRepository.save(apolice);

        // Get all the apoliceList
        restApoliceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apolice.getId())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fim").value(hasItem(DEFAULT_FIM.toString())))
            .andExpect(jsonPath("$.[*].placaVeiculo").value(hasItem(DEFAULT_PLACA_VEICULO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(sameNumber(DEFAULT_VALOR))));
    }

    @Test
    void getApolice() throws Exception {
        // Initialize the database
        apoliceRepository.save(apolice);

        // Get the apolice
        restApoliceMockMvc
            .perform(get(ENTITY_API_URL_ID, apolice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apolice.getId()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.intValue()))
            .andExpect(jsonPath("$.inicio").value(DEFAULT_INICIO.toString()))
            .andExpect(jsonPath("$.fim").value(DEFAULT_FIM.toString()))
            .andExpect(jsonPath("$.placaVeiculo").value(DEFAULT_PLACA_VEICULO))
            .andExpect(jsonPath("$.valor").value(sameNumber(DEFAULT_VALOR)));
    }

    @Test
    void getNonExistingApolice() throws Exception {
        // Get the apolice
        restApoliceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewApolice() throws Exception {
        // Initialize the database
        apoliceRepository.save(apolice);

        int databaseSizeBeforeUpdate = apoliceRepository.findAll().size();

        // Update the apolice
        Apolice updatedApolice = apoliceRepository.findById(apolice.getId()).get();
        updatedApolice
            .numero(UPDATED_NUMERO)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .placaVeiculo(UPDATED_PLACA_VEICULO)
            .valor(UPDATED_VALOR);

        restApoliceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApolice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApolice))
            )
            .andExpect(status().isOk());

        // Validate the Apolice in the database
        List<Apolice> apoliceList = apoliceRepository.findAll();
        assertThat(apoliceList).hasSize(databaseSizeBeforeUpdate);
        Apolice testApolice = apoliceList.get(apoliceList.size() - 1);
        assertThat(testApolice.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testApolice.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testApolice.getFim()).isEqualTo(UPDATED_FIM);
        assertThat(testApolice.getPlacaVeiculo()).isEqualTo(UPDATED_PLACA_VEICULO);
        assertThat(testApolice.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    void putNonExistingApolice() throws Exception {
        int databaseSizeBeforeUpdate = apoliceRepository.findAll().size();
        apolice.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApoliceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apolice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apolice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apolice in the database
        List<Apolice> apoliceList = apoliceRepository.findAll();
        assertThat(apoliceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchApolice() throws Exception {
        int databaseSizeBeforeUpdate = apoliceRepository.findAll().size();
        apolice.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApoliceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apolice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apolice in the database
        List<Apolice> apoliceList = apoliceRepository.findAll();
        assertThat(apoliceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamApolice() throws Exception {
        int databaseSizeBeforeUpdate = apoliceRepository.findAll().size();
        apolice.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApoliceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apolice)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apolice in the database
        List<Apolice> apoliceList = apoliceRepository.findAll();
        assertThat(apoliceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateApoliceWithPatch() throws Exception {
        // Initialize the database
        apoliceRepository.save(apolice);

        int databaseSizeBeforeUpdate = apoliceRepository.findAll().size();

        // Update the apolice using partial update
        Apolice partialUpdatedApolice = new Apolice();
        partialUpdatedApolice.setId(apolice.getId());

        partialUpdatedApolice
            .numero(UPDATED_NUMERO)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .placaVeiculo(UPDATED_PLACA_VEICULO)
            .valor(UPDATED_VALOR);

        restApoliceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApolice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApolice))
            )
            .andExpect(status().isOk());

        // Validate the Apolice in the database
        List<Apolice> apoliceList = apoliceRepository.findAll();
        assertThat(apoliceList).hasSize(databaseSizeBeforeUpdate);
        Apolice testApolice = apoliceList.get(apoliceList.size() - 1);
        assertThat(testApolice.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testApolice.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testApolice.getFim()).isEqualTo(UPDATED_FIM);
        assertThat(testApolice.getPlacaVeiculo()).isEqualTo(UPDATED_PLACA_VEICULO);
        assertThat(testApolice.getValor()).isEqualByComparingTo(UPDATED_VALOR);
    }

    @Test
    void fullUpdateApoliceWithPatch() throws Exception {
        // Initialize the database
        apoliceRepository.save(apolice);

        int databaseSizeBeforeUpdate = apoliceRepository.findAll().size();

        // Update the apolice using partial update
        Apolice partialUpdatedApolice = new Apolice();
        partialUpdatedApolice.setId(apolice.getId());

        partialUpdatedApolice
            .numero(UPDATED_NUMERO)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .placaVeiculo(UPDATED_PLACA_VEICULO)
            .valor(UPDATED_VALOR);

        restApoliceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApolice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApolice))
            )
            .andExpect(status().isOk());

        // Validate the Apolice in the database
        List<Apolice> apoliceList = apoliceRepository.findAll();
        assertThat(apoliceList).hasSize(databaseSizeBeforeUpdate);
        Apolice testApolice = apoliceList.get(apoliceList.size() - 1);
        assertThat(testApolice.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testApolice.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testApolice.getFim()).isEqualTo(UPDATED_FIM);
        assertThat(testApolice.getPlacaVeiculo()).isEqualTo(UPDATED_PLACA_VEICULO);
        assertThat(testApolice.getValor()).isEqualByComparingTo(UPDATED_VALOR);
    }

    @Test
    void patchNonExistingApolice() throws Exception {
        int databaseSizeBeforeUpdate = apoliceRepository.findAll().size();
        apolice.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApoliceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, apolice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apolice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apolice in the database
        List<Apolice> apoliceList = apoliceRepository.findAll();
        assertThat(apoliceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchApolice() throws Exception {
        int databaseSizeBeforeUpdate = apoliceRepository.findAll().size();
        apolice.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApoliceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apolice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apolice in the database
        List<Apolice> apoliceList = apoliceRepository.findAll();
        assertThat(apoliceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamApolice() throws Exception {
        int databaseSizeBeforeUpdate = apoliceRepository.findAll().size();
        apolice.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApoliceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(apolice)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apolice in the database
        List<Apolice> apoliceList = apoliceRepository.findAll();
        assertThat(apoliceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteApolice() throws Exception {
        // Initialize the database
        apoliceRepository.save(apolice);

        int databaseSizeBeforeDelete = apoliceRepository.findAll().size();

        // Delete the apolice
        restApoliceMockMvc
            .perform(delete(ENTITY_API_URL_ID, apolice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Apolice> apoliceList = apoliceRepository.findAll();
        assertThat(apoliceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

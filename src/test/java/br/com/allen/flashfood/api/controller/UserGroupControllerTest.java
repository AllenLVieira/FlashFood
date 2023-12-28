package br.com.allen.flashfood.api.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import br.com.allen.flashfood.api.assembler.FamilyModelAssembler;
import br.com.allen.flashfood.api.model.response.FamilyResponse;
import br.com.allen.flashfood.domain.model.Family;
import br.com.allen.flashfood.domain.model.User;
import br.com.allen.flashfood.domain.repository.FamilyRepository;
import br.com.allen.flashfood.domain.service.FamilyRegistrationService;
import br.com.allen.flashfood.domain.service.UserRegistrationsService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserGroupController.class})
@ExtendWith(SpringExtension.class)
class UserGroupControllerTest {
  User user;
  Family family;
  Family family2;
  FamilyResponse familyResponse;
  FamilyResponse familyResponse2;
  @Autowired private UserGroupController underTest;
  @MockBean private FamilyModelAssembler familyModelAssembler;
  @MockBean private UserRegistrationsService userRegistrationsService;
  @MockBean private FamilyRegistrationService familyRegistrationService;
  @MockBean private FamilyRepository familyRepository;

  @BeforeEach
  void setUp() {
    user = new User();
    user.setEmail("allenvieira96@gmail.com");
    user.setGroups(new HashSet<>());
    user.setId(1L);
    user.setName("Allen");
    user.setPassword("123456");
    user.setRegistrationDate(null);

    family = new Family();
    family.setId(1L);
    family.setName("Group");
    family.setPermissions(new HashSet<>());

    familyResponse = new FamilyResponse();
    familyResponse.setId(1L);
    familyResponse.setName("Group");

    family2 = new Family();
    family2.setId(2L);
    family2.setName("Group");
    family2.setPermissions(new HashSet<>());

    familyResponse2 = new FamilyResponse();
    familyResponse2.setId(2L);
    familyResponse2.setName("Group");

    user.getGroups().add(family);
  }

  /** Method under test: {@link UserGroupController#linkGroup(Long, Long)} */
  @Test
  void testLinkGroup() throws Exception {
    doNothing().when(userRegistrationsService).linkGroup(any(), any());
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.put("/users/{userId}/groups/{groupId}", 123L, 123L);
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(underTest).build().perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  /** Method under test: {@link UserGroupController#unlinkGroup(Long, Long)} */
  @Test
  void testUnlinkGroup() throws Exception {
    doNothing().when(userRegistrationsService).unlinkGroup(any(), any());
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.delete("/users/{userId}/groups/{groupId}", 123L, 123L);
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(underTest).build().perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  /** Method under test: {@link UserGroupController#getAllGroups(Long)} */
  @Test
  void testGetAllGroups() throws Exception {
    List<FamilyResponse> results = new ArrayList<>();
    results.add(familyResponse);
    results.add(familyResponse2);
    when(userRegistrationsService.findUserOrElseThrow(any())).thenReturn(user);
    when(familyModelAssembler.toCollectionModel(any())).thenReturn(results);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/users/{userId}/groups", 1L);
    MockMvcBuilders.standaloneSetup(underTest)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(jsonPath("$", Matchers.hasSize(2)))
        .andExpect(jsonPath("$[0].name", Matchers.equalTo("Group")));
  }
}

package com.glos.filemanagerservice.controllers;

import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.entities.Tag;
import com.glos.filemanagerservice.facade.TagFacade;
import com.glos.filemanagerservice.requestFilters.TagRequestFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagController.class)
@ExtendWith(MockitoExtension.class)
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagFacade tagFacade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTags() throws Exception {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("testTag");

        Page<Tag> tagsPage = new Page<>();
        tagsPage.setContent(List.of(tag));
        tagsPage.setTotalElements(1L);
        tagsPage.setTotalElements(1);

        when(tagFacade.getTagsByFilter(any(TagRequestFilter.class))).thenReturn(tagsPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/tags")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0]").value("testTag"));
    }

    @Test
    public void testPutTag() throws Exception {
        when(tagFacade.putTag(eq("testTag"))).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(put("/tags/testTag"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTag() throws Exception {
        when(tagFacade.deleteTag(eq("testTag"))).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(MockMvcRequestBuilders.delete("/tags/testTag"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddTag() throws Exception {
        String rootFullName = "someRoot";
        String tagName = "myTag";

        mockMvc.perform(MockMvcRequestBuilders.put("/tags/" + rootFullName + "/add-tag/" + tagName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testRemoveTag() throws Exception {
        String rootFullName = "someRoot";
        String tagName = "myTag";
        mockMvc.perform(MockMvcRequestBuilders.delete("/tags/" + rootFullName + "/remove-tag/" + tagName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
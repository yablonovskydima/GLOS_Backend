package com.glos.databaseAPIService.domain.service;

import com.glos.databaseAPIService.domain.entities.Tag;
import com.glos.databaseAPIService.domain.entityMappers.TagMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * 	@author - yablonovskydima
 */
@Service
public class TagService implements CrudService<Tag, Long>
{
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Autowired
    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    public Optional<Tag> getByName(String name)
    {
        return tagRepository.findByName(name);
    }

    Tag getTagByIdOrThrow(Long id)
    {
        return getById(id).orElseThrow(() -> { return new ResourceNotFoundException("Tag is not found"); });
    }

    public Page<Tag> findAllByFilter(Tag tag, Pageable page)
    {
        Example<Tag> exampleTag = Example.of(tag, ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher.of(
                        ExampleMatcher.StringMatcher.CONTAINING
                ))
                .withIgnoreCase()
                .withIgnoreNullValues());
        return tagRepository.findAll(exampleTag, page);
    }

    @Transactional
    @Override
    public Tag create(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    public Map.Entry<Tag, Boolean> ensure(String name) {
        final Optional<Tag> tagOpt = tagRepository.findByName(name);
        return Map.entry(tagOpt.orElseGet(() -> tagRepository.save(new Tag(null, name))), tagOpt.isEmpty());
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> getAll(EntityFilter filter) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    @Override
    public void deleteById(Long id)
    {
        getTagByIdOrThrow(id);
        tagRepository.deleteById(id);
    }
    @Override
    public Optional<Tag> getById(Long id) {
        return tagRepository.findById(id);
    }

    @Transactional
    @Override
    public Tag update(Long id, Tag newTag) {
        Tag tag = getTagByIdOrThrow(id);
        tagMapper.transferEntityDto(newTag, tag);
        return tagRepository.save(tag);
    }
}

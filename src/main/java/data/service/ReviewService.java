package data.service;

import data.dto.ReviewDto;
import data.dto.ReviewResponseDto;
import data.entity.GoodseulEntity;
import data.entity.ReviewEntity;
import data.entity.UserEntity;
import data.repository.GoodseulRepository;
import data.repository.ReviewRepository;
import data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final GoodseulRepository goodseulRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, GoodseulRepository goodseulRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.goodseulRepository = goodseulRepository;
        this.userRepository = userRepository;
    }

    public Map<String, Object> getPageReview(int page, int size, String sortProperty, String sortDirection, String keyword) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortProperty));
        Page<ReviewEntity> result;


        if (keyword != null && !keyword.trim().isEmpty()) {
            result = reviewRepository.findByGIdx_GoodseulNameContainingAndUIdx_NameContaining(keyword, keyword, pageable);
        } else {
            result = reviewRepository.findAll(pageable);
        }

        log.info("Getting page review with keyword: " + keyword);

        List<ReviewResponseDto> reviewDtos = result.getContent().stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviewDtos);
        response.put("totalElements", result.getTotalElements());
        response.put("totalPages", result.getTotalPages());
        response.put("currentPage", result.getNumber() + 1);
        response.put("hasNext", result.hasNext());

        return response;
    }

    public ReviewDto insertReview(ReviewDto dto) {
        GoodseulEntity goodseul = goodseulRepository.findById(dto.getG_idx()).orElse(null);
        UserEntity user = userRepository.findById(dto.getU_idx()).orElse(null);

        if (goodseul == null || user == null) {
            throw new RuntimeException("구슬님이나 유저를 찾을 수 없어요!");
        }

        ReviewEntity review = ReviewEntity.toReviewEntity(dto, goodseul, user);
        reviewRepository.save(review);
        log.info(dto.toString() + "서비스");
        log.info(review.toString());
        return dto;
    }


}
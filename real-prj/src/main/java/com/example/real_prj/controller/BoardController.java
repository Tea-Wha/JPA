package com.example.real_prj.controller;


import com.example.real_prj.dto.BoardReqDto;
import com.example.real_prj.dto.BoardResDto;
import com.example.real_prj.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/board")
@AllArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/")
    public int boardEntirePage(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size){
        return boardService.getBoardPageCount(page, size);
    }

//    @GetMapping("/")
//    public ResponseEntity<Integer> boardEntirePageRes(@RequestParam(defaultValue = "0") int page,
//                                   @RequestParam(defaultValue = "10") int size){
//        return ResponseEntity.ok(boardService.getBoardPageCount(page, size));
//    }

    @PostMapping("/post")
    public boolean boardPost(@RequestBody BoardReqDto boardReqDto){
        return boardService.saveBoard(boardReqDto);
    }

//    @PostMapping("/post")
//    public ResponseEntity<Boolean> boardPostRes(@RequestBody BoardReqDto boardReqDto){
//        return ResponseEntity.ok(boardService.saveBoard(boardReqDto));
//    }

    @PostMapping("/view/{id}")
    public BoardResDto boardView(@PathVariable Long id){
        return boardService.findByBoardId(id);
    }

//    @PostMapping("/view/{id}")
//    public ResponseEntity<BoardResDto> boardViewRes(@PathVariable Long id){
//        return ResponseEntity.ok(boardService.findByBoardId(id));
//    }

    @GetMapping("/viewall")
    public List<BoardResDto> boardViewAll(){
        return boardService.findAllBoard();
    }

//    @GetMapping("/viewall")
//    public ResponseEntity<List<BoardResDto>> boardViewAllRes(){
//        return ResponseEntity.ok(boardService.findAllBoard());
//    }

    @PostMapping("/search")
    public List<BoardResDto> boardSearch(@RequestParam String keyword){
        return boardService.searchBoard(keyword);
    }

//    @PostMapping("/search")
//    public ResponseEntity<List<BoardResDto>> boardSearchRes(@RequestBody String keyword){
//        return ResponseEntity.ok(boardService.searchBoard(keyword));
//    }

    @PostMapping("/page")
    public List<BoardResDto> boardPage(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size){
        return boardService.pagingBoardList(page, size);
    }

//    @PostMapping("/page")
//    public ResponseEntity<List<BoardResDto>> boardPageRes(@RequestParam(defaultValue = "0") int page,
//                                       @RequestParam(defaultValue = "10") int size){
//        return ResponseEntity.ok(boardService.pagingBoardList(page, size));
//    }

    @PostMapping("/delete/{id}")
    public boolean boardDelete(@PathVariable Long id, @RequestParam String email){
        return boardService.boardDelete(id, email);
    }

//    @PostMapping("/delete/{id}")
//    public ResponseEntity<Boolean> boardDeleteRes(@PathVariable Long id, @RequestParam String email){
//        return ResponseEntity.ok(boardService.boardDelete(id, email));
//    }

    @PostMapping("/modify/{id}")
    public boolean boardModify(@RequestBody BoardReqDto boardReqDto, @PathVariable Long id){
        return boardService.modifyBoard(boardReqDto, id);
    }

//    @PostMapping("/modify/{id}")
//    public ResponseEntity<Boolean> boardModifyRes(@RequestBody BoardReqDto boardReqDto, @PathVariable Long id){
//        return ResponseEntity.ok(boardService.modifyBoard(boardReqDto, id));
//    }

    @PostMapping("/search2")
    public List<BoardResDto> boardSearch2(@RequestParam String title, @RequestParam String content){
        return boardService.searchTitleOrContent(title, content);
    }

//    @PostMapping("/search2")
//    public ResponseEntity<List<BoardResDto>> boardSearch2Res(@RequestParam String title, @RequestParam String content){
//        return ResponseEntity.ok(boardService.searchTitleOrContent(title, content));
//    }
}

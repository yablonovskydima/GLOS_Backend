package com.glos.filemanagerservice.DTO;


import java.util.ArrayList;
import java.util.List;

public class MoveRequest
{
    public static class MoveNode {
        private String from;
        private String to;

        public MoveNode() {
        }

        public MoveNode(String from, String to) {
            this.from = from;
            this.to = to;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }
    }

    private List<MoveNode> moves;

    public MoveRequest()
    {
        this.moves = new ArrayList<>();
    }

    public MoveRequest(List<MoveNode> moves) {
        this.moves = moves;
    }

    public List<MoveNode> getMoves() {
        return moves;
    }

    public void setMoves(List<MoveNode> moves) {
        this.moves = moves;
    }
}

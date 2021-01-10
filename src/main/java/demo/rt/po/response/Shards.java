package demo.rt.po.response;


import lombok.Data;

@Data
public class Shards {
    private int total;
    private int successful;
    private int skipped;
    private int failed;
}
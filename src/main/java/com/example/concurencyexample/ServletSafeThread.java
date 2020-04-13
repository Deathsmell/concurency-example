package com.example.concurencyexample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/example")
public class ServletSafeThread {
    private BigInteger lastNumber;
    private BigInteger[] lastFactors;
    private long hits;
    private long cacheHits;

    public synchronized long getHits() {
        return hits;
    }

    public synchronized double getCacheHitRatio() {
        return (double) cacheHits / (double) hits;
    }

    @GetMapping("/main")
    public String main (){
        return "firstExample";
    }

    @GetMapping("/1")
    public String example(@RequestParam(value = "number", required = false) BigInteger number, Model model) {
        BigInteger i = number;
        BigInteger[] factors = null;
        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                ++cacheHits;
                factors = lastFactors.clone();
//                Arrays.stream(factors).forEach(System.out::println);
            }
        }
        if (factors == null) {
            factors = factor(number);
            synchronized (this){
                lastNumber = i;
                lastFactors = factors.clone();
//                Arrays.stream(factors).forEach(System.out::println);
            }
        }
        List<BigInteger> result = new ArrayList<>(Arrays.asList(factors));
        model.addAttribute("result", result);
        model.addAttribute("count", getHits());
        model.addAttribute("cacheCount",getCacheHitRatio());
        return "firstExample";
    }


    public BigInteger[] factor(BigInteger number) {
        long valueExact = number.longValueExact();
        List<Integer> promis = new ArrayList<>();
        int c = 0;
        for (int i = 1; i <= valueExact; ++i) {
            if (valueExact % i == 0) {
                promis.add(i);
            }
        }
        BigInteger[] result = new BigInteger[promis.size()];
        for (Integer res: promis
             ) {
            result[c++] = BigInteger.valueOf(res);
        }
        return result;
    }
}
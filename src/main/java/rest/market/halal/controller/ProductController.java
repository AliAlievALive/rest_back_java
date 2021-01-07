package rest.market.halal.controller;

import org.springframework.web.bind.annotation.*;
import rest.market.halal.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("product")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
    private int counter = 4;

    private List<Map<String, String>> products = new ArrayList<>() {{
        add(new HashMap<>() {{
            put("id", "1");
            put("name", "Бэлеш");
        }});
        add(new HashMap<>() {{
            put("id", "2");
            put("name", "Олэш");
        }});
        add(new HashMap<>() {{
            put("id", "3");
            put("name", "Очпычмак");
        }});
    }};

    @GetMapping
    public List<Map<String, String>> getProducts() {
        return products;
    }

    @GetMapping("{id}")
    public Map<String, String> getProduct(@PathVariable String id) {
        return findProduct(id);
    }

    private Map<String, String> findProduct(String id) {
        return products.stream()
                .filter(product -> product.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> product) {
        product.put("id", String.valueOf(counter++));
        products.add(product);

        return product;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> product) {
        Map<String, String> productFromDb = findProduct(id);
        productFromDb.putAll(product);
        productFromDb.put("id", id);

        return productFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> product = findProduct(id);
        products.remove(product);
    }
}

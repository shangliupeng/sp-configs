package cn.tedu.sp04.order.feignclient;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.web.util.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "item-server" ,fallback = ItemFeignServiceFB.class)
public interface ItemFeignService  {
    @GetMapping("/{orderId}")
    JsonResult<List<Item>> getItems(@PathVariable String orderId);

    @PostMapping("/decreaseNumber")
    JsonResult decreaseNumber(@RequestBody List<Item> items);








}

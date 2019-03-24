package cn.liuzhengwei.ebook.service;

import cn.liuzhengwei.ebook.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 添加订单
    @Override
    public void addOrder(Order order) {
        int id;
        try {
            id = jdbcTemplate.queryForObject("select max(ID) from ORDERS", Integer.class) + 1;
        } catch(Exception e){
            id = 0;
        }

        jdbcTemplate.update("insert into ORDERS(ID, ACCOUNT, ISBN, COUNT, DATE) values(?, ?, ?, ?, ?i)",
                id, order.getAccount(), order.getISBN(), order.getCount(), order.getDate());
    }

    // 删除订单
    @Override
    public void deleteOrder(Integer id) {
        jdbcTemplate.update("delete from ORDERS where ID="+id);
    }

    // 获取所有订单
    @Override
    public List<Order> getAllOrders() {
        RowMapper<Order> rowMapper = new BeanPropertyRowMapper<>(Order.class);
        List<Order> orders = jdbcTemplate.query("select * from ORDERS",rowMapper);
        return orders;
    }

    // 获取指定用户订单
    @Override
    public List<Order> getOrder(String account) {
        RowMapper<Order> rowMapper = new BeanPropertyRowMapper<>(Order.class);
        List<Order> orders = jdbcTemplate.query("select * from ORDERS where ACCOUNT='"+account+"'",rowMapper);
        return orders;
    }
}

package work.iruby.wshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.iruby.common.dao.DataMessage;
import work.iruby.common.dao.PageMessage;
import work.iruby.common.enums.DataStatus;
import work.iruby.wshop.entity.Shop;
import work.iruby.wshop.entity.User;
import work.iruby.wshop.mapper.ShopMapper;
import work.iruby.wshop.service.IShopService;
import work.iruby.wshop.service.UserContext;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author iruby
 * @since 2021-04-12
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {
    @Override
    public DataMessage<Shop> creatShop(Shop shop) {
        shop.setId(null);
        shop.setOwnerUserId(getCurrentUserId(""));
        shop.setStatus(DataStatus.OK.value());
        shop.setCreatedAt(LocalDateTime.now());
        shop.setUpdatedAt(LocalDateTime.now());
        save(shop);
        return DataMessage.of(shop);
    }

    @Override
    public DataMessage<Shop> deleteShopByShopId(Long shopId) {
        Shop shop = checkCurrentUserPermission(shopId, "");
        shop.setStatus(DataStatus.DELETED.value());
        shop.setUpdatedAt(LocalDateTime.now());
        updateById(shop);
        return DataMessage.of(getById(shopId));
    }

    @Override
    public DataMessage<Shop> updateShopByShopId(Long shopId, Shop shop) {
        checkCurrentUserPermission(shopId, "");
        updateById(shop);
        return DataMessage.of(getById(shopId));
    }

    @Override
    public PageMessage<List<Shop>> getShopsCurrentUser(Integer pageNum, Integer pageSize) {
        Long currentUserId = getCurrentUserId("");
        Page<Shop> shopPage = new Page<>();
        shopPage.setCurrent(pageNum);
        shopPage.setSize(pageSize);
        QueryWrapper<Shop> queryWrapper;
        queryWrapper = new QueryWrapper<Shop>().
                eq("owner_user_id", currentUserId).
                eq("status", DataStatus.OK.value());
        page(shopPage, queryWrapper);
        return PageMessage.of(pageNum, pageSize, (int) shopPage.getPages(), shopPage.getRecords());
    }

    @Override
    public DataMessage<Shop> getShopByShopId(Long shopId) {
        Shop shop = checkCurrentUserPermission(shopId, "");
        return DataMessage.of(shop);
    }

    private Long getCurrentUserId(String msg) {
        User currentUser = UserContext.getCurrentUser();
        if (currentUser != null) {
            return currentUser.getId();
        } else {
            throw new RuntimeException(msg);
        }
    }

    private Shop checkCurrentUserPermission(Long shopId, String msg) {
        if (shopId == null) {
            return null;
        }
        Shop shop = getById(shopId);
        if (shop == null || !DataStatus.OK.value().equals(shop.getStatus())) {
            return null;
        }
        if (!getCurrentUserId("").equals(shop.getOwnerUserId())) {
            throw new RuntimeException(msg);
        }

        return shop;
    }
}

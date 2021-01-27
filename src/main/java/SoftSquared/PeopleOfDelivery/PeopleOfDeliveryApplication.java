package SoftSquared.PeopleOfDelivery;

import SoftSquared.PeopleOfDelivery.domain.coupon.Coupon;
import SoftSquared.PeopleOfDelivery.domain.coupon.CouponRepository;
import SoftSquared.PeopleOfDelivery.domain.menu.Menu;
import SoftSquared.PeopleOfDelivery.domain.menu.MenuRepository;
import SoftSquared.PeopleOfDelivery.domain.order.Orders;
import SoftSquared.PeopleOfDelivery.domain.order.OrdersRepository;
import SoftSquared.PeopleOfDelivery.domain.store.Store;
import SoftSquared.PeopleOfDelivery.domain.store.StoreRepository;
import SoftSquared.PeopleOfDelivery.domain.user.User;
import SoftSquared.PeopleOfDelivery.domain.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PeopleOfDeliveryApplication implements CommandLineRunner{

	private final UserRepository userRepository;
	private final StoreRepository storeRepository;
	private final OrdersRepository ordersRepository;
	private final CouponRepository couponRepository;
	private final MenuRepository menuRepository;

	public PeopleOfDeliveryApplication(UserRepository userRepository,
									   StoreRepository storeRepository,
									   OrdersRepository ordersRepository,
									   CouponRepository couponRepository,
									   MenuRepository menuRepository) {
		this.userRepository = userRepository;
		this.storeRepository = storeRepository;
		this.ordersRepository = ordersRepository;
		this.couponRepository = couponRepository;
		this.menuRepository = menuRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(PeopleOfDeliveryApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception{
		User user = userRepository.save(new User("testName1","test1@naver.com","123321","010-1111-2222"
				,"서울시송파구잠실6동", 1,1,"1995-03-10",1));

		couponRepository.save(Coupon.builder()
				.user(user)
				.coupon1000(5)
				.coupon3000(5)
				.coupon5000(0)
				.build());

		/**
		 * 주문 많은순
		 * store 2개 추가
		 * order가 1개, 2개
		 * return은 2개, 개순
		 */
	Store store =	storeRepository.save(Store.builder()
				.id(1L)
				.name("testStore1")
				.phoneNumber("010-1111-1111")
				.location("서울시")
				.lowBoundPrice(5000)
				.deliveryFee(3000)
				.description("testCase입니다.")
				.user(userRepository.findById(1L).orElseGet(null))
				.imageURL("testURI")
				.status(1)
				.build());
		storeRepository.save(Store.builder()
				.id(2L)
				.name("testStore2")
				.phoneNumber("010-1111-1112")
				.location("서울시")
				.lowBoundPrice(4000)
				.deliveryFee(4000)
				.description("testCase2입니다.")
				.user(userRepository.findById(1L).orElseGet(null))
				.imageURL("testURI")
				.status(1)
				.build());

		menuRepository.save(Menu.builder()
				.store(store)
				.popularCheck(1)
				.description("test1")
				.price(30000)
				.imageURL("default")
				.status(1)
				.name("test1")
				.imageStatus(2)
				.build());

		menuRepository.save(Menu.builder()
				.store(store)
				.popularCheck(1)
				.description("test2")
				.price(20000)
				.imageURL("default")
				.status(1)
				.name("test2")
				.imageStatus(1)
				.build());

		menuRepository.save(Menu.builder()
				.store(store)
				.popularCheck(1)
				.description("test3")
				.price(10000)
				.imageURL("default")
				.status(1)
				.name("test3")
				.imageStatus(1)
				.build());


	}

}

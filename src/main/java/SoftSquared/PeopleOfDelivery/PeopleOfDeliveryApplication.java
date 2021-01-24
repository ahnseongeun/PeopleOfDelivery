package SoftSquared.PeopleOfDelivery;

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

	public PeopleOfDeliveryApplication(UserRepository userRepository,
									   StoreRepository storeRepository,
									   OrdersRepository ordersRepository) {
		this.userRepository = userRepository;
		this.storeRepository = storeRepository;
		this.ordersRepository = ordersRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(PeopleOfDeliveryApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception{
		userRepository.save(new User("testName1","test1@naver.com","123321","010-1111-2222"
				,"서울시송파구잠실6동", 1,1,"1995-03-10",1));

		/**
		 * 주문 많은순
		 * store 2개 추가
		 * order가 1개, 2개
		 * return은 2개, 개순
		 */
		storeRepository.save(Store.builder()
				.id(1L)
				.name("testStore1")
				.phoneNumber("010-1111-1111")
				.location("서울시")
				.lowBoundPrice(5000)
				.deliveryFee(3000)
				.description("testCase입니다.")
				.user(userRepository.findById(1L).orElseGet(null))
				.imageURI("testURI")
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
				.imageURI("testURI")
				.status(1)
				.build());

		ordersRepository.save(Orders.builder()
				.id(1L)
				.request_content("test1")
				.user(userRepository.findById(1L).orElseGet(null))
				.store(storeRepository.findById(1L).orElseGet(null))
				.status((byte) 1)
				.orderDetails(null)
				.reviews(null)
				.payment(null)
				.address("test1")
				.build());

		ordersRepository.save(Orders.builder()
				.id(2L)
				.request_content("test12")
				.user(userRepository.findById(1L).orElseGet(null))
				.store(storeRepository.findById(2L).orElseGet(null))
				.status((byte) 1)
				.orderDetails(null)
				.reviews(null)
				.payment(null)
				.address("test2")
				.build());

		ordersRepository.save(Orders.builder()
				.id(3L)
				.request_content("test3")
				.user(userRepository.findById(1L).orElseGet(null))
				.store(storeRepository.findById(2L).orElseGet(null))
				.status((byte) 1)
				.orderDetails(null)
				.reviews(null)
				.payment(null)
				.address("test3")
				.build());
	}

}

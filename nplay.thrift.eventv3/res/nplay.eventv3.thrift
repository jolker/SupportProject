include "TServiceBase2.thrift"

namespace java com.bliss.nplay.service.eventv3.thrift

struct TEventV3Value {
	1: i32 eventId,
	2: EEventUserGroup eventUserGroup,
	3: string title,
	4: string description,
	5: string imgUrl,
	6: EEventType eventType,
	7: EPaymentType paymentType,
	8: i16 frequency,
	9: byte priority,
	10: EButtonAction buttonAction,
	11: string buttonText,
	12: string buttonLink,
	13: i64 dateBegin,
	14: i64 dateEnd,
	15: EEventStatus eventStatus,
	16: string userCreated,
	17: i64 dateCreated,
	18: string userUpdated,
	19: i64 dateUpdated,
	20: i64 dateLastRun
}


enum EEventType {
	BANNER = 1,
	POPUP = 2,
	HEADER = 3
}

enum EButtonAction {
	OPEN_BROWSER = 1,
	OPEN_WEBVIEW = 2,
	OPEN_SHOP = 3,
	OPEN_GAME = 4
}

enum EEventStatus {	
	INACTIVE = 0,
	ACTIVE = 1
}

enum EPaymentType {
	ALL_USER = 0
	INAPP_USER = 1,
	CARD_USER = 2,
}

service TEventV3 extends TServiceBase2.TServiceBase2 {	
//common client
	list<TEventV3Value> getBanner(1: TServiceBase2.TUserMiniProfile userProfile),
	list<TEventV3Value> getPopup(1: TServiceBase2.TUserMiniProfile userProfile),
	TEventV3Value getEvent(1: i32 eventId),
	list<TEventV3Value> getAllEvent(1: i64 dateBegin, 2: i64 dateEnd, 3: i16 page, 4: i16 offset)

//admin
	TEventV3Value addEvent(1: TServiceBase2.OpAuth auth, 2: TEventV3Value event),
    bool updateEvent(1: TServiceBase2.OpAuth auth, 2: TEventV3Value event);
    bool updateStatus(1: TServiceBase2.OpAuth auth, 2: i32 eventID, EEventStatus status);
}

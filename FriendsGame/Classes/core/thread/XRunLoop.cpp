
#include "XRunLoop.h"


RunLoop* RunLoop::s_instance = nullptr;

RunLoop* RunLoop::getInstance(){
	if(nullptr == s_instance){
		s_instance = new RunLoop();
	}
	return s_instance;
}

RunLoop::RunLoop(){
	Director* director = Director::getInstance();
	Scheduler* sched = director->getScheduler();
	sched->schedule(CC_SCHEDULE_SELECTOR(RunLoop::dispatchObserver), this, 0.0f, false);
}

void RunLoop::addObserver(RunLoopObserver* observer){
	m_mutext.lock();
	Ref* obj = dynamic_cast<Ref*>(observer);
	if(obj)
		obj->retain();
	m_queueObserver.push_back(observer);
	m_mutext.unlock();
}

void RunLoop::dispatchObserver(float){
	RunLoopObserver* observer = this->front();
	if (observer)
		observer->operate();
	this->pop();
}

RunLoopObserver* RunLoop::front(){
	RunLoopObserver* observer = nullptr;
	m_mutext.lock();
	if (m_queueObserver.size() > 0)
		observer = m_queueObserver.front();
	m_mutext.unlock();
	return observer;
}

void RunLoop::pop(){
	m_mutext.lock();
	if (m_queueObserver.size() > 0){
		RunLoopObserver* observer = m_queueObserver.front();
		Ref* obj = dynamic_cast<Ref*>(observer);
		if (obj)
			obj->release();
		else
			delete observer;
		m_queueObserver.pop_front();
	}
	m_mutext.unlock();
}

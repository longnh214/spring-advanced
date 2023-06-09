package hello.advanced.app.v1;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV1 {
  private final HelloTraceV1 trace;

  public void save(String itemId) {
    //저장 로직
    TraceStatus status = null;

    try{
      status = trace.begin("OrderRepository.save()");

      if(itemId.equals("ex")){
        throw new IllegalArgumentException("예외 발생!!!");
      }
      sleep(1000);

      trace.end(status);
    }catch(Exception e){
      trace.exception(status, e);
      throw e; // 예외를 다시 던져주어야 한다. 안 던지면 예외를 먹어보리고, 이후에 정상 흐름으로 동작해버린다.
    }
  }

  private void sleep(int millis){
    try{
      Thread.sleep(millis);
    }catch(InterruptedException e){
      e.printStackTrace();
    }
  }
}

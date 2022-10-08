package com.example.terdlor_first_bot.stateMachine

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.action.Action
import org.springframework.statemachine.config.EnableStateMachine
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer
import org.springframework.statemachine.listener.StateMachineListener
import org.springframework.statemachine.listener.StateMachineListenerAdapter
import org.springframework.statemachine.state.State
import java.util.*


@Configuration
@EnableStateMachine
class StateMachineConfiguration : EnumStateMachineConfigurerAdapter<States, Events>() {

    @Throws(Exception::class)
    override fun configure(config: StateMachineConfigurationConfigurer<States?, Events?>) {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(listener())
    }

    override fun configure(states: StateMachineStateConfigurer<States, Events>) {
        states
                .withStates()
                .initial(States.SI)
                .end(States.S2)
                .states(EnumSet.allOf(States::class.java))
    }

    @Throws(Exception::class)
    override fun configure(transitions: StateMachineTransitionConfigurer<States, Events>
    ) {
        transitions
                .withExternal()
                .source(States.SI).target(States.S1).event(Events.E1).action(initAction())
                .and()
                .withExternal()
                .source(States.S1).target(States.S2).event(Events.E2)
    }

    @Bean
    fun initAction(): Action<States, Events>? {
        return Action { ctx -> System.out.println(ctx.getTarget().getId()) }
    }

    @Bean
    fun listener(): StateMachineListener<States?, Events?>? {
        return object : StateMachineListenerAdapter<States?, Events?>() {
            override fun stateChanged(from: State<States?, Events?>?, to: State<States?, Events?>) {
                System.out.println("State change to " + to.getId())
            }
        }
    }
}
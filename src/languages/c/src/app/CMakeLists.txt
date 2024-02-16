
set(TARGET app)

add_executable(${TARGET}
    app.c
)

target_link_libraries(${TARGET}
    rmq
)

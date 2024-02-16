
#include <stdlib.h>
#include "cli_options_parser.h"

#define ARRAY_LENGTH(array) (sizeof(array) / sizeof(array[0]))

cop__option *
parse_options(int argc, char *argv[])
{
    static cop__option options[] = {
        {.definition = {.short_option = 'a', .long_option = "add", .has_argument = COP__NO_ARGUMENT}},
        {.definition = {.short_option = 'd', .long_option = "delete", .has_argument = COP__REQUIRED_ARGUMENT}},
        {.definition = {.short_option = COP__SHORT_OPTION_NULL, .long_option = "temp", .has_argument = COP__NO_ARGUMENT}},
        {.definition = {.short_option = 'c', .long_option = "collect", .has_argument = COP__OPTIONAL_ARGUMENT}},
    };

    cop__parse(argc, argv, options, ARRAY_LENGTH(options));

    return options;
}

int
run(int argc, char *argv[])
{
    cop__option *options;

    options = parse_options(argc, argv);

    return EXIT_SUCCESS;
}


#ifndef CLI_OPTIONS_PARSER_H
#define CLI_OPTIONS_PARSER_H

#include <stdbool.h>
#include <stdio.h>

#define COP__SHORT_OPTION_NULL EOF

typedef enum cop__status
{
    COP_STATUS__SUCCESS,
    COP_STATUS__INVALID_PARAMETER,
} cop__status;

typedef enum cop__has_argument_options
{
    COP__NO_ARGUMENT,
    COP__REQUIRED_ARGUMENT,
    COP__OPTIONAL_ARGUMENT,
} cop__has_argument_options;

typedef struct cop__option_definition
{
    int                        short_option;
    const char                *long_option;
    cop__has_argument_options  has_argument;
} cop__option_definition;

typedef struct cop__option_result
{
    bool        encountered;
    const char *value;
} cop__option_result;

typedef struct cop__option
{
    cop__option_definition definition;
    cop__option_result     result;
} cop__option;

cop__status
cop__parse(int argc, char *argv[], cop__option *options, size_t options_len);

#endif // CLI_OPTIONS_PARSER_H
